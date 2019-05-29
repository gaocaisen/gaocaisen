package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import dao.ModelDAO;
import entity.Model;
import entity.ModelVO;
import mvc.RequestMapping;

public class ModelController {
	
	@RequestMapping("/model/addModel.do")
	public String addModel(HttpServletRequest request) {
		String mName = request.getParameter("mName");
		int sMid = Integer.valueOf(request.getParameter("sMid"));
		
		Model model = new Model();
		model.setmName(mName);
		model.setsMid(sMid);
		
		ModelDAO dao = new ModelDAO();
		String mid = dao.addModel(model);
		
		return "json:"+mid;
	}
	
	@RequestMapping("/model/addLevelOneModel.do")
	public String addLevelOneModel(HttpServletRequest request) {
		String mName = request.getParameter("mName");
		
		Model model = new Model();
		model.setmName(mName);
		model.setsMid(0);
		
		ModelDAO dao = new ModelDAO();
		String mid = dao.addModel(model);
		
		return "json:"+mid;
	}
	
	
	@RequestMapping("/model/delModel.do")
	public String delModel(HttpServletRequest request) {
		int mid = Integer.valueOf(request.getParameter("mid"));
		
		ModelDAO dao = new ModelDAO();
		boolean flag = dao.delModel(mid);
		
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/model/deleteAllModelsInOneLevel.do")
	public String deleteAllModelsInOneLevel(HttpServletRequest request) {
		int sMid = Integer.valueOf(request.getParameter("sMid"));
		
		ModelDAO dao = new ModelDAO();
		boolean flag = dao.deleteAllModelsInOneLevel(sMid);
		
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/model/changeModel.do")
	public String changeModel(HttpServletRequest request) {
		int mid = Integer.valueOf(request.getParameter("mid"));
		String mName = request.getParameter("mName");
		int sMid = Integer.valueOf(request.getParameter("sMid"));
		
		Model model = new Model();
		model.setmName(mName);
		model.setsMid(sMid);
		
		ModelDAO dao = new ModelDAO();
		boolean flag = dao.changeModel(mid, model);
		
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/model/listLevelOneModel.do")
	public String listLevelOneModel(HttpServletRequest request) {
		ModelDAO dao = new ModelDAO();
		List<Model> list = dao.listLevelOneModel();
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/model/listAllModels.do")
	public String listAllModels(HttpServletRequest request) {
		ModelDAO dao = new ModelDAO();
		List<Model> list = dao.listAllModels();
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/model/getModelsBysMid.do")
	public String getModelsBysMid(HttpServletRequest request) {
		int sMid = Integer.valueOf(request.getParameter("sMid"));
		ModelDAO dao = new ModelDAO();
		List<ModelVO> list = dao.getModelsBysMid(sMid);
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/model/getModelByMid.do")
	public String getModelByMid(HttpServletRequest request) {
		int mid = Integer.valueOf(request.getParameter("mid"));
		ModelDAO dao = new ModelDAO();
		Model model = dao.getModelByMid(mid);
		
		return "json:"+getJson(model);
	}
	
	private String getJson(Object obj) {
		String jsonOutput = "";
		if(obj!=null) {
			 jsonOutput= JSON.toJSONString(obj);
		}
		
		return jsonOutput;
	}
	
	
	
	
	
}
