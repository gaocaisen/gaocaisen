package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import dao.ArticleDAO;
import dao.IndexDAO;
import dao.ModelDAO;
import entity.Article;
import entity.Model;
import mvc.RequestMapping;

public class IndexController {
	
	@RequestMapping("/index/listArticles.exc")
	public String listArticle(HttpServletRequest request) {
		//Integer count = Integer.parseInt(request.getParameter("count"));
		int page = 1;
		int num = 13;
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.listArticles(page, num);
		
		return "json:"+getJson(list);
	}
	
	//首页
	@RequestMapping("/index/listArticlesInAllMore.exc")
	public String listArticlesInAllMore(HttpServletRequest request) {
		Integer page = Integer.parseInt(request.getParameter("count"));
		int num = 27;
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.listArticles(page, num);
		
		return "json:"+getJson(list);
	}
	
	//这是首页用的
	@RequestMapping("/index/getArticlesByMid.exc")
	public String getArticlesByMid(HttpServletRequest request) {
		int page = 1;
		int num = 8;
		Integer mid = Integer.parseInt(request.getParameter("mid"));
		
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.getArticlesByMid(mid, page, num);
		
		return "json:"+getJson(list);
	}
	
	//这是具体某个栏目更多用的
	@RequestMapping("/index/getArticlesByMidInModelMore.exc")
	public String getArticlesByMidInModelMore(HttpServletRequest request) {
		int num = 16;
		Integer mid = Integer.parseInt(request.getParameter("mid"));
		Integer page = Integer.parseInt(request.getParameter("count"));
		
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.getArticlesByMid(mid, page, num);
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/index/getArticleByAid.exc")
	public String getArticleByAid(HttpServletRequest request) {
		int aid = Integer.valueOf(request.getParameter("aid"));
		
		IndexDAO dao = new IndexDAO();
		Article art = dao.getArticleByAid(aid);
		
		return "json:"+getJson(art);
	}
	
	
	
	@RequestMapping("/index/listAllModels.exc")
	public String listAllModels(HttpServletRequest request) {
		int page = 1;
		int num = 10;
		IndexDAO dao = new IndexDAO();
		List<Model> list = dao.listAllModels(page, num);
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/index/getModelByMid.exc")
	public String getModelByMid(HttpServletRequest request) {
		int mid = Integer.valueOf(request.getParameter("mid"));
		
		IndexDAO dao = new IndexDAO();
		Model model = dao.getModelByMid(mid);
		
		return "json:"+getJson(model);
	}
	
	//所有的更多页面中，查询总文章数的方法
	@RequestMapping("/index/getTotalRowsFromArticle.exc")
	public String getTotalRowsFromArticle(HttpServletRequest request){
		IndexDAO dao = new IndexDAO();
		int rows = dao.getTotalRowsFromArticle();
		
		return "json:"+rows;
	}
	
	@RequestMapping("/index/getTotalRowsFromArticleByMid.exc")
	public String getTotalRowsFromArticleByMid(HttpServletRequest request){
		Integer mid = Integer.parseInt(request.getParameter("mid"));
		IndexDAO dao = new IndexDAO();
		int rows = dao.getTotalRowsFromArticleByMid(mid);
		
		return "json:"+rows;
	}
	
	
	private String getJson(Object obj) {
		String jsonOutput = "";
		if(obj!=null) {
			 jsonOutput= JSON.toJSONString(obj);
		}
		
		return jsonOutput;
	}
	
	

}
