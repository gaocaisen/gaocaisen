package model;

import java.util.List;

import org.junit.Test;

import dao.ModelDAO;
import entity.Model;
import entity.ModelVO;

public class Test1 {
	
	@Test
	public void addModel() {
		ModelDAO dao = new ModelDAO();
		Model model = new Model(null,"¿ÆÑ§",0);
		String mid = dao.addModel(model );
		System.out.println(mid);
	}
	
	@Test
	public void delModel() {
		ModelDAO dao = new ModelDAO();
		boolean flag = dao.delModel(4);
		System.out.println(flag);
	}
	
	@Test
	public void changeModel() {
		ModelDAO dao = new ModelDAO();
		Model model = new Model(null,"¿¼¹Å",0);
		boolean flag = dao.changeModel(5,model );
		System.out.println(flag);
	}
	
	@Test
	public void listModel() {
		ModelDAO dao = new ModelDAO();
		List<Model> list = dao.listLevelOneModel();
		for (Model model : list) {
			System.out.println(model);
		}
	}
	
	@Test
	public void getModelsBysMid() {
		ModelDAO dao = new ModelDAO();
		List<ModelVO> list = dao.getModelsBysMid(1);
		for (ModelVO model : list) {
			System.out.println(model);
		}
	}
	
	

}
