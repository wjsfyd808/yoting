package org.kitchen.service;

import java.util.List;

import org.kitchen.domain.Criteria_w;
import org.kitchen.domain.RecipeVO;
import org.kitchen.domain.UserVO;
import org.kitchen.mapper.RecipeMapper_w;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class RecipeServiceImpl_w implements RecipeService_w {
	
	@Setter(onMethod_ = @Autowired)
	private RecipeMapper_w mapper;

	@Override
	public List<RecipeVO> getList(Criteria_w cri) {
		log.info("get list with criteria: " + cri);
		return mapper.getListWithPaging(cri);
	}

	@Override
	public List<UserVO> getUserList(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getUserList(cri);
	}

	@Override
	public List<?> getAll(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getAll(cri);
	}

	@Override
	public List<RecipeVO> getTagNum(Criteria_w cri) {
		// TODO Auto-generated method stub
		Long tno = mapper.getTagNum(cri);
		List<RecipeVO> recipeList = null;
		if(tno!=null) {
			List<Long> rno = mapper.getRnoByTagNum(tno);
			if(rno!=null)	
				recipeList = mapper.getRecipeByRno(rno);
		}
		return recipeList;
	}

	@Override
	public List<Long> getRnoByTagNum(Long tagNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeVO> moreRecipeList(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getRecipeListWithPaging(cri);
	}

	@Override
	public int getTotalRecipe(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalRecipeCount(cri);
	}

	@Override
	public List<UserVO> moreUserList(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getUserListWithPaging(cri);
	}

	@Override
	public int getTotalUser(Criteria_w cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalUserCount(cri);
	} 
	
	
	
	
}
