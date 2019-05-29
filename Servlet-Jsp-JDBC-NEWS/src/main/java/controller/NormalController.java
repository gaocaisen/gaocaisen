package controller;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;

import dao.ArticleDAO;
import dao.NormalDAO;
import entity.Article;
import entity.User;
import mvc.RequestMapping;

public class NormalController {
	
	@RequestMapping("/article/addUserArticle.do")
	public String addUserArticle(HttpServletRequest request) {
		int uId = getUidFromSession(request);
		String title = "";
		String article = "";
		Integer mid = 1;
		String newFileName="";
		
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "D:\\\\eclipse-jee-oxygen-2-win32-x86_64\\\\upload";	
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
		
		return "redirect:/normalUser-article.jsp";
	}
	
	@RequestMapping("/article/changeUserArticle.do")
	public String changeUserArticle(HttpServletRequest request) {
		String title = "";
		String article = "";
		Integer mid = 1;
		Integer aid = -1;
		String fileUpName= "";
		String noChangeImage = "";
		//String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String savePath = "D:\\\\eclipse-jee-oxygen-2-win32-x86_64\\\\upload";	
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
		
		return "redirect:/normalUser-article.jsp";
	}
	
	
	
	@RequestMapping("/article/listUserArticle.do")
	public String listUserArticle(HttpServletRequest request) {
		Integer count = Integer.parseInt(request.getParameter("count"));
		Integer uId = Integer.parseInt(request.getParameter("uId"));
		NormalDAO dao = new NormalDAO();
		List<Article> list = dao.listUserArticle(uId,count);
		
		return "json:"+getJson(list);
	}
	
	@RequestMapping("/article/getTotalRowsFromNormal.do")
	public String getTotalRowsFromNormal(HttpServletRequest request) {
		Integer uId = getUidFromSession(request);
		NormalDAO dao = new NormalDAO();
		int rows = dao.getTotalRowsFromNormal(uId);
		
		return "json:"+rows;
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
		System.out.println();
		return user.getuId();
	}

}
