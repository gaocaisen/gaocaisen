package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;

import dao.ArticleDAO;
import dao.UserDAO;
import entity.Article;
import entity.User;
import mvc.RequestMapping;

public class ArticleController {
	
	@RequestMapping("/article/addArticle.do")
	public String addArticle(HttpServletRequest request) {
		int uId = getUidFromSession(request);
		String title = "";
		String article = "";
		Integer mid = 1;
		String newFileName="";
		
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "D:\\eclipse-jee-oxygen-2-win32-x86_64\\upload";	
		File parentFile = new File(savePath);
		if(!parentFile.exists()) {
			 //����ļ��в����ڣ��򴴽��ļ���
			 parentFile.mkdirs();
		}
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);  //enctype�����Ƿ���multipart/form-data
	    //������ϴ����ļ�
	    if(isMultipart) {
			try {
				//����ʵ��
				FileItemFactory factory = new DiskFileItemFactory();  
				//ServletFileUploadʵ��������FileItemFactory����
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            //�������ֶΣ���װ��һ��FileItemʵ���ļ���
	            List<FileItem> itemList = upload.parseRequest(request);
	            //������
				Iterator<FileItem> iterator = itemList.iterator();
				while(iterator.hasNext()) {
					//���ν���ÿһ��FileItemʵ���������ֶ�
					FileItem fileItem = iterator.next();
					//����Ǳ���ͨ�ֶ�
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("title")) {
							title = fileItem.getString("UTF-8");
						}else if(fileItem.getFieldName().equals("article")) {
							article = fileItem.getString("UTF-8");
						}else if(fileItem.getFieldName().equals("mid")) {
							mid = Integer.parseInt(fileItem.getString("UTF-8"));
						}
					}else {
						//�����file
						String originalFileName=fileItem.getName();
						if(!"".equals(originalFileName)) {
							int index=originalFileName.lastIndexOf(".");
							String suffix=originalFileName.substring(index);
							newFileName=(new Date()).getTime()+suffix;
							/*fileUpName = UUID.randomUUID().toString()+fileItem.getName();*/  //�û��ϴ����ļ���
							File file = new File(savePath+File.separator+newFileName);
							if(!file.exists()) {
								file.createNewFile();
							}
							fileItem.write(file);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		Article art = new Article();
		art.setuId(uId);
		art.setTitle(title);
		art.setArticle(article);
		art.setMid(mid);
		art.setImage("/upload/"+newFileName);
		
		ArticleDAO dao = new ArticleDAO();
		dao.addArticle(art);
		
		return "redirect:/superUser-article.jsp";
	}
	
	@RequestMapping("/article/delArticle.do")
	public String delArticle(HttpServletRequest request) {
		int aid = Integer.valueOf(request.getParameter("aid"));
		ArticleDAO dao = new ArticleDAO();
		boolean flag = dao.delArticle(aid);
		
		return flag?"json:true":"json:false";
	}
	
	@RequestMapping("/article/changeArticle.do")
	public String changeArticle(HttpServletRequest request) {
		String title = "";
		String article = "";
		Integer mid = 1;
		Integer aid = -1;
		String fileUpName= "";
		String noChangeImage = "";
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "D:\\eclipse-jee-oxygen-2-win32-x86_64\\upload";	
		File parentFile = new File(savePath);
		if(!parentFile.exists()) {
			 //����ļ��в����ڣ��򴴽��ļ���
			 parentFile.mkdirs();
		}
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);  //enctype�����Ƿ���multipart/form-data
	    //������ϴ����ļ�
	    if(isMultipart) {
			try {
				//����ʵ��
				FileItemFactory factory = new DiskFileItemFactory();  
				//ServletFileUploadʵ��������FileItemFactory����
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            //�������ֶΣ���װ��һ��FileItemʵ���ļ���
	            List<FileItem> itemList = upload.parseRequest(request);
	            //������
				Iterator<FileItem> iterator = itemList.iterator();
				while(iterator.hasNext()) {
					//���ν���ÿһ��FileItemʵ���������ֶ�
					FileItem fileItem = iterator.next();
					//����Ǳ���ͨ�ֶ�
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("title")) {
							title = fileItem.getString("UTF-8");
						}else if(fileItem.getFieldName().equals("article")) {
							article = fileItem.getString("UTF-8");
						}else if(fileItem.getFieldName().equals("mid")) {
							mid = Integer.parseInt(fileItem.getString("UTF-8"));
						}else if(fileItem.getFieldName().equals("aid")) {
							aid = Integer.parseInt(fileItem.getString("UTF-8"));
						}else if(fileItem.getFieldName().equals("noChangeImage")) {
							noChangeImage = fileItem.getString("UTF-8");
						}
					}else {
						//�����file
						String originalFileName = fileItem.getName();
						if(!"".equals(originalFileName)) {
							int index = originalFileName.lastIndexOf(".");
							String suffix=originalFileName.substring(index);
							fileUpName = (new Date()).getTime()+suffix;
							/*fileUpName = UUID.randomUUID().toString()+fileItem.getName();*/  //�û��ϴ����ļ���
							File file = new File(savePath+File.separator+fileUpName);
							if(!file.exists()) {
								file.createNewFile();
							}
							fileItem.write(file);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		Article art = new Article();
		art.setTitle(title);
		art.setArticle(article);
		art.setMid(mid);
		if(!"".equals(fileUpName)) {
			art.setImage("/upload/"+fileUpName);
		}else{
			art.setImage(noChangeImage);
		}
		
		ArticleDAO dao = new ArticleDAO();
		dao.changeArticle(aid, art);
		
		return "redirect:/superUser-article.jsp";
	}
	
	@RequestMapping("/article/listArticle.do")
	public String listArticle(HttpServletRequest request) {
		Integer count = Integer.parseInt(request.getParameter("count"));
		ArticleDAO dao = new ArticleDAO();
		List<Article> list = dao.listArticle(count);
		
		return "json:"+getJson(list);
	}
	
	
	@RequestMapping("/article/getArticleByAid.do")
	public String getArticleByAid(HttpServletRequest request) {
		int aid = Integer.valueOf(request.getParameter("aid"));
		ArticleDAO dao = new ArticleDAO();
		Article art = dao.getArticleByAid(aid);
		
		return "json:"+getJson(art);
	}
	
	@RequestMapping("/article/getTotalRowsFromArticle.do")
	public String getTotalRowsFromArticle(HttpServletRequest request){
		ArticleDAO dao = new ArticleDAO();
		int rows = dao.getTotalRowsFromArticle();
		
		return "json:"+rows;
	}
	
	//���ĳ�����£������µĵ������1
	@RequestMapping("/index/addaddAccount.exc")
	public String addAccount(HttpServletRequest request) {
		int aid = Integer.valueOf(request.getParameter("aid"));
		ArticleDAO dao = new ArticleDAO();
		dao.addAccound(aid);
		
		return "account";
	}
	
	private String getJson(Object obj) {
		String jsonOutput = "";
		if(obj!=null) {
			 jsonOutput= JSON.toJSONString(obj);
		}
		return jsonOutput;
	}
	
	private int getUidFromSession(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		return user.getuId();
	}

}
