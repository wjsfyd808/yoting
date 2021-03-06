package org.kitchen.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kitchen.domain.ContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ContentMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private ContentMapper mapper;
	
//	@Test
	public void testGetList()
	{
		mapper.getList().forEach(content -> log.info(content));
	}
	
//	@Test
	public void testInsert()
	{
		ContentVO content = new ContentVO();
		
		content.setStepNo(1);
		content.setRno(41L);
		content.setPhotoUrl("https://www.mediafine.co.kr/news/photo/201608/4153_8093_3852.jpg");
		content.setContent("바질페스토 파스타 step1");
		
		mapper.insert(content);
		
		log.info(content);
	}
	
//	@Test
	public void testRead()
	{
		List<ContentVO> content = mapper.read(3L);
		
		log.info(content);
	}
	
//	@Test
	public void testDelete()
	{
		log.info("DELETE COUNT : " + mapper.delete(41L));
	}
	
	@Test
	public void testUpdate()
	{
		List<ContentVO> contentList = mapper.read(41L);
		int step = 1;
		ContentVO content = contentList.get(step-1);
		
//		log.info("번호 : " +content.getStepNo());
		
		content.setRno(content.getRno());
		content.setStepNo(content.getStepNo());
		content.setContent("바질페스토 파스타 step1 수정22");
		content.setPhotoUrl("수정22");
		
		log.info(content);
		
		int count = mapper.update(content);
		log.info("UPDATE COUNT : " + count);
	}
}
