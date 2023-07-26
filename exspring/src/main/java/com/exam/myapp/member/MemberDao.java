package com.exam.myapp.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

	List<MemberVo> selectMemberList();

	int insertMember(MemberVo vo);

	int deleteMember(String memId);

	MemberVo selectMember(String memId);
	
	int updateMember(MemberVo vo);

	MemberVo selectLogin(MemberVo vo);
}