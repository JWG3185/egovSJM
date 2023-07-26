package com.exam.myapp.bbs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional	// 이 객체의 모든 메서드들이 각각 하나의 트랜잭션으로 정의
public class BbsServiceImpl implements BbsService{
	
	@Autowired
	private BbsDao bbsDao;
	
	@Autowired
	private AttachDao attachDao;
	
	@Value("${bbs.upload.path}")	// 스프링이 지정한 값을 변수에 주입(저장)
	private String uploadPath;		// 게시판 첨부파일 저장 위치
	
	@PostConstruct	// 스프링이 현재 객체의 초기화 작업이 완료된 후 실행
	public void init() {
		new File(uploadPath).mkdirs();	// uploadPath 디렉토리가 없으면 생성
	}

	@Override
	public List<BbsVo> selectBbsList(SearchVo svo) {
		return bbsDao.selectBbsList(svo);
	}

//	@Transactional	// 이 메서드를 하나의 트랜잭션으로 정의(각각 나눌 때)
	@Override
	public int insertBbs(BbsVo vo) {
		
		int num = bbsDao.insertBbs(vo);
		
		for (MultipartFile file : vo.getBbsFile()) {
			if(file.getSize() <= 0) {
				continue;	// 파일 크기가 0인 경우, 저장하지 않음
			}
			
			System.out.println("파일명 : " + file.getOriginalFilename() );
			System.out.println("파일크기 : " + file.getSize() );
			
			String fname = null;	// 중복될 확률이 극도로 낮은 랜덤 문자열 생성
			File saveFile = null;
			do {
				fname = UUID.randomUUID().toString();
				saveFile = new File(uploadPath, fname);
			} while (saveFile.exists());	// uploadPath 밑에 fname라는 이름으로 저장
			
			
			try {
				file.transferTo(saveFile);	// 파일 file의 내용을 saveFile에 복사(저장)
				
				AttachVo attVo = new AttachVo();
				attVo.setAttOrgName(file.getOriginalFilename() );	// 첨부파일 원래 이름
				attVo.setAttBbsNo(vo.getBbsNo());	// 첨부파일이 속한 게시글 번호
				attVo.setAttNewName(fname);	// 첨부파일 저장 이름
				attachDao.insertAttach(attVo);
				
			} 
			catch (IllegalStateException | IOException e) {
//				e.printStackTrace();
				throw new RuntimeException(e);	// 첨부파일 저장 중 오류 발생시 트랜잭션이 롤백되도록
			}
		}
		return num;
	}

	@Override
	public int deleteBbs(BbsVo vo) {
		BbsVo bbsVo = bbsDao.selectBbs(vo.getBbsNo());	// 삭제할 게시글 정보 조회
		
		if(!vo.getBbsWriter().equals(bbsVo.getBbsWriter())) {
			return 0;
		}
		
		
		
		
		for (AttachVo attVo : bbsVo.getAttachList()) {	// 게시글의 첨부파일을 하나씩 꺼내서
			new File(uploadPath, attVo.getAttNewName()).delete();	// 디스크에서 첨부파일 삭제
			attachDao.deleteAttach(attVo.getAttNo() );	// 테이블에서 첨부파일 삭제
		}
		
		return bbsDao.deleteBbs(vo.getBbsNo());	// 테이블에서 게시글 삭제
	}

	@Override
	public BbsVo selectBbs(int bbsNo) {
		return bbsDao.selectBbs(bbsNo);
	}

	@Override
	public int updateBbs(BbsVo vo) {
		return bbsDao.updateBbs(vo);
	}

	@Override
	public AttachVo selectAttach(int attNo) {
		return attachDao.selectAttach(attNo);
	}

	@Override
	public File getAttachFile(AttachVo vo) {
		return new File(uploadPath, vo.getAttNewName() );
	}

	@Override
	public int selectBbsCount(SearchVo svo) {
		
		return bbsDao.selectBbsCount(svo);
	}

}
