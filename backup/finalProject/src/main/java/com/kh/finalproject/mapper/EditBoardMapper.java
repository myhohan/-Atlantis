package com.kh.finalproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.BoardImg;

@Mapper
public interface EditBoardMapper {

	
	/** 게시글 부분 작성 SQL 수행
	 * @param inputBoard
	 * @return
	 */
	
	int boardInsert(Board inputBoard);
	
	/** 게시글 이미지 모두 삽입 SQL 수행
	 * 	@param uploadList
	 *  @return
	 */
	
	int insertUplodList(List<BoardImg> uploadList);
	
	/** 게시글 부분 수정 SQL (제목/내용)
	 * @param inputBoard
	 * @return
	 */
	
	int boardUpdate(Board inputBoard);
	
	/** 게시글 이미지 삭제 SQL
	 * @param map
	 * @return
	 */
	
	int deleteImage(Map<String, Object> map);
	
	/** 게시글 이미지 수정 SQL
	 * @param img
	 * @return
	 */
	int updateImage(BoardImg img);
	
	
	/** 게시글 이미지 삽입 SQL
	 * @param img
	 * @return
	 */
	int insertImage(BoardImg img);
	
	/** 게시글 삭제
	 * @param map
	 * @return
	 */
	int boardDelete(Map<String, Integer> map);
	
	
}
