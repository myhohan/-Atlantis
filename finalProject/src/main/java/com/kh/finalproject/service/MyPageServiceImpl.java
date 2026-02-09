package com.kh.finalproject.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Mypage;
import com.kh.finalproject.dto.UploadFile;
import com.kh.finalproject.mapper.MyPageMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageProfileService {

	
	@Autowired
	private MyPageMapper mapper;
	
	//BCrypt 암호화 객체 의존성 주입(SecurityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // /myPage/profile/
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // C:/uploadFiles/profile/
	
	// 회원 정보 수정
	@Override
    public int updateInfo(Member inputMember, String[] memberAddress) {
        
        // [수정 전 문제 코드] 
        // if (!inputMember.getMemberAddress().equals(",,")) { ... }
        // -> inputMember 안에는 주소가 없어서 이 코드가 실행되지 않았음!

        // ▼▼▼ [수정 후 정답 코드] 받아온 배열(memberAddress)을 확인해야 함 ▼▼▼
        if (memberAddress != null && memberAddress.length > 0) {
            // 1. 배열 요소를 "^^^" 구분자로 합침 (예: 12345^^^서울시^^^강남구)
            String address = String.join("^^^", memberAddress);
            
            // 2. 합친 주소를 Member 객체에 세팅
            inputMember.setMemberAddress(address);
        } else {
            // 주소가 아예 안 넘어왔으면 null 처리
            inputMember.setMemberAddress(null);
        }
        
        // 3. DB 업데이트 실행
        return mapper.updateInfo(inputMember);
    }
	
	// 비밀번호 변경 서비스
		@Override
		public int changePw(Map<String, Object> paramMap, int memberNo) {

			// 1. 현재 비밀번호가 일치하는지 확인하기
			// - 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
			String originPw = mapper.selectPw(memberNo);

			// 입력받은 현재 비밀번호와(평문)
			// DB에서 조회한 비밀번호(암호화)를 비교
			// -> BCryptPasswordEncoder.matches(평문, 암호화된비밀번호) 사용

			// 다를 경우
			if (!bcrypt.matches((String) paramMap.get("currentPw"), originPw)) {
				return 0;
			}

			// 2. 같을경우

			// 새 비밀번호를 암호화 (BCryptPasswordEncoder.encode(평문))
			String encPw = bcrypt.encode((String) paramMap.get("newPw"));

			// 진행후 DB에 업데이트
			// SQL 전달해야하는 데이터 2개 (암호화한 새 비밀번호, 회원번호)
			// -> SQL 전달 인자 1개뿐!
			// -> 묶어서 전달 (paramMap 재활용)

			paramMap.put("encPw", encPw);
			paramMap.put("memberNo", memberNo);

			return mapper.changePw(paramMap);
		}

		// 회원 탈퇴 서비스
		@Override
		public int secession(String memberPw, int memberNo) {

			// 1. 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
			String encPw = mapper.selectPw(memberNo);

			// 2. 입력받은 비번 & 암호화된 DB 비번 같은지 비교

			// 다를 경우
			if (!bcrypt.matches(memberPw, encPw)) {
				return 0;
			}

			// 같은 경우
			return mapper.secession(memberNo);
		}

		// 파일 업로드 테스트 1
		@Override
		public String fileUpload1(MultipartFile uploadFile) throws Exception {

			if (uploadFile.isEmpty()) { // 업로드한 파일이 없을 경우
				return null;
			}

			// 업로드한 파일이 있을 경우
			// C:/uploadFiles/test/파일명 으로 서버에 저장
			uploadFile.transferTo(new File("C:/uploadFiles/test/" + uploadFile.getOriginalFilename()));

			// C:/uploadFiles/test/하기와.jpg

			// 웹 에서 해당 파일에 접근할 수 있는 경로를 만들어 반환

			// 이미지가 최종 저장된 서버 컴퓨터상의 경로
			// C:/uploadFiles/test/파일명.jpg

			// 클라이언트가 브라우저에 해당 이미지를 보기위해 요청하는 경로
			// ex) <img src="경로">
			// /myPage/file/파일명.jpg -> <img src="/myPage/file/파일명.jpg">

			return "/myPage/file/" + uploadFile.getOriginalFilename();
		}

		// 파일 업로드 테스트 2 (서버 저장, DB 저장)
		@Override
		public int fileUpload2(MultipartFile uploadFile, int memberNo) throws Exception {

			// MultipartFile이 제공하는 메소드
			// - isEmpty() : 업로드된 파일이 없을경우 true / 있다면 false
			// - getSize() : 파일 크기
			// - getOriginalFileName() : 원본 파일명
			// - transferTo(경로) :
			// 메모리 또는 임시 저장 경로에 업로드된 파일을
			// 원하는 경로에 실제로 전송(서버 어떤 폴더에 저장할지 지정)

			// 업로드된 파일이 없다면
			if (uploadFile.isEmpty()) {
				return 0;
			}

			// 업로드된 파일이 있다면

			// 1. 서버에 저장될 서버 폴더 경로 만들기

			// 파일이 저장될 서버 폴더 경로
			String folderPath = "C:/uploadFiles/test/";

			// 클라이언트가 파일이 저장된 폴더에 접근할 수 있는 주소(요청 주소)
			String webPath = "/myPage/file/";

			// 2. DB에 전달할 데이터를 DTO로 묶어서 INSERT
			// webPath, memberNo, 원본파일명, 변경된파일명
			String fileRename = Utility.fileRename(uploadFile.getOriginalFilename());

			// Builder 패턴을 이용해서 UploadFile 객체 생성
			// 장점 1) 반복되는 참조변수명, set 구문 생략
			// 장점 2) method chaining을 이용하여 한줄로 작성 가능
			UploadFile uf = UploadFile.builder()
					.memberNo(memberNo)
					.filePath(webPath)
					.fileOriginalName(uploadFile.getOriginalFilename())
					.fileRename(fileRename)
					.build();

			int result = mapper.insertUploadFile(uf);

			// 3. 삽입 (INSERT) 성공 시 파일을 지정된 서버 폴더에 저장
			// 삽입 실패 시
			if (result == 0)
				return 0;

			// 삽입 성공 시
			// C:/uploadFiles/test/변경된파일명 으로
			// 파일을 서버 컴퓨터에 저장!
			uploadFile.transferTo(new File(folderPath + fileRename));
			// C:/uploadFiles/test/20251211100330_00001.jpg

			return result; // 1
		}

		
		// 파일 목록 조회 서비스
		@Override
		public List<UploadFile> fileList(int memberNo) {
			return mapper.fileList(memberNo);
		}
		
		// 여러 파일 업로드 서비스
		@Override
		public int fileUpload3(List<MultipartFile> aaaList, 
							List<MultipartFile> bbbList, 
							int memberNo) throws Exception {
			
			// 1. aaaList 처리
			int result1 = 0;
			
			// 업로드된 파일이 없을 경우를 제외하고 업로드
			for(MultipartFile file : aaaList) {
				
				if(file.isEmpty()) { // 파일이 없으면 다음 파일
					continue; // 아래 코드 수행 X 다음반복으로 넘어감..
				}
				
				// fileUpload2() 메서드 호출(재활용)
				// -> 파일 하나 업로드 + DB INSERT
				result1 += fileUpload2(file, memberNo);
				
			}
			
			// 2. bbbList 처리
			int result2 = 0;
			
			for(MultipartFile file : bbbList) {
				
				if(file.isEmpty()) continue;
				
				result2 += fileUpload2(file, memberNo);
			}
			
			return result1 + result2;
		}
		
		// 프로필 이미지 변경 서비스
		@Override
		public int profile(MultipartFile profileImg, Member loginMember) throws Exception {
			
			// 프로필 이미지 경로 (수정할 경로)
			String updatePath = null; 
			
			// 변경명 저장
			String rename = null;
			
			// 업로드한 이미지가 있을 경우
			if( !profileImg.isEmpty() ) {
				// updatePath 경로 조합 
				
				// 1. 파일명 변경
				rename = Utility.fileRename(profileImg.getOriginalFilename());
				
				// 2. /myPage/profile/변경된파일명
				updatePath = profileWebPath + rename;
			}
			
			// 수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체
			Member member = Member.builder()
							.memberNo(loginMember.getMemberNo())
							.profileImg(updatePath)
							.build();
				
			
			// UPDATE 수행
			int result = mapper.profile(member);
			
			if(result > 0) { // DB에 업데이트 성공
				
				// 프로필 이미지를 없앤 경우(NULL로 수정한 경우)를 제외
				// -> 업로드한 이미지가 있을 경우
				if( !profileImg.isEmpty() ) {
					// 파일을 서버 지정된 폴더에 저장
					profileImg.transferTo(new File(profileFolderPath + rename));
									// C:/uploadFiles/profile/변경한이름
				}
				
				// 세션에 등록된 현재 로그인한 회원 정보에서 
				// 프로필 이미지 경로를 DB에 업데이트한 경로로 변경
				loginMember.setProfileImg(updatePath);
				
			} 
			
			return result;
		}
		
		
		@Override
	    public Mypage selectProfile(int memberNo) {
	        return mapper.selectProfile(memberNo);
	    }
		
		@Override
	    public List<DeliveryRequest> selectMyParcelList(int memberNo) {
	        return mapper.selectMyParcelList(memberNo);
	    }

	    @Override
	    public List<DeliveryRequest> selectMyPaymentList(int memberNo) {
	        return mapper.selectMyPaymentList(memberNo);
	    }
		
		
		

	}

	
	
	
	

