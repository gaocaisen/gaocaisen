package article;

import java.util.List;

import org.junit.Test;

import dao.ArticleDAO;
import entity.Article;

public class Test1 {
	
	@Test
	public void addArticle() {
		ArticleDAO dao = new ArticleDAO();
		Article art = new Article();
		art.setuId(5);
		art.setTitle("我爱我家");
		art.setArticle("卫生靠大家");
		art.setMid(1);
		String aid = dao.addArticle(art);
		System.out.println(aid);
	}
	
	@Test
	public void delArticle() {
		ArticleDAO dao = new ArticleDAO();
		boolean flag = dao.delArticle(2);
		System.out.println(flag);
	}
	
	@Test
	public void changeArticle() {
		ArticleDAO dao = new ArticleDAO();
		Article art = new Article();
		art.setTitle("爱在落日黄昏");
		art.setArticle("爱在黎明破晓");
		art.setMid(1);
		boolean flag = dao.changeArticle(1, art );
		System.out.println(flag);
	}
	
	@Test
	public void listArticle() {
		ArticleDAO dao = new ArticleDAO();
		List<Article> list = dao.listArticle(1);
		for (Article article : list) {
			System.out.println(article);
		}
	}
	
	@Test
	public void getArticleByAid() {
		ArticleDAO dao = new ArticleDAO();
		Article art = dao.getArticleByAid(1);
		System.out.println(art);
	}

}
