package com.kh.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.mapper.AdminMapper;

@Service
public interface AdminService {

    
    /**
     * 
     * @param inputMember
     * @return
     */
    Member login(Member inputMember);
    
    /**
     * 
     * @param memberEmail
     * @return
     */
    int checkEmail(String memberEmail);
    
    /**
     * 
     * @param member
     * @return
     */
    String createAdminAccount(Member member);
    
    /**
     * 
     * @return
     */
    List<Member> adminAccountList();
    
    /**
     * 
     * @return
     */
    Board maxReadCount();
    
    /**
     * 
     * @return
     */
    List<Member> selectWithdrawnMemberList();
    
    /**
     * 
     * @param memberNo
     * @return
     */
    int restoreMember(int memberNo);
    
    
}