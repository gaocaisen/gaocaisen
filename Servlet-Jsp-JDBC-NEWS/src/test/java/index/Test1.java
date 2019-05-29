package index;

import java.util.List;

import org.junit.Test;

import dao.IndexDAO;
import entity.Article;

public class Test1 {

	@Test
	public void listArticles(){
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.listArticles(1, 10);
		for (Article article : list) {
			System.out.println(article);
		}
	}
	
	@Test
	public void getArticleByMid() {
		IndexDAO dao = new IndexDAO();
		List<Article> list = dao.getArticlesByMid(1,1,1);
		for (Article article : list) {
			System.out.println(article);
		}
	}
}
