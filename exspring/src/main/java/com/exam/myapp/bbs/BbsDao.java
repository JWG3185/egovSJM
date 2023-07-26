package com.exam.myapp.bbs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BbsDao {

	List<BbsVo> selectBbsList(SearchVo svo);

	int insertBbs(BbsVo vo);

	int deleteBbs(int bbsNo);

	BbsVo selectBbs(int bbsNo);
	
	int updateBbs(BbsVo vo);

	int selectBbsCount(SearchVo svo);

}