package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Book;
import Model.DBUtils;
@WebServlet("/")
public class BookManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
	    try {
            switch (action) 
            {
            case "/detail":		// 
                showBook(request, response);
                break;
            case "/new":		// 
                showNewForm(request, response);
                break;
            case "/insert":
                insertBook(request, response);
                break;
            case "/delete":
                deleteBook(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateBook(request, response);
                break;
            case "/list":
                listBook(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
		
	}
	
	
	private void listBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<Book> listBook =DBUtils.getByAll();  
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllBook.jsp");
        request.setAttribute("listBook", listBook);
        dispatcher.forward(request, response);
    }
 
	
	private void showBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
        Book book2Show = DBUtils.getByID(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookDetails.jsp");
        request.setAttribute("book", book2Show);
        dispatcher.forward(request, response);
    }
	
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookAdd.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = DBUtils.getByID(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookEdit.jsp");
        request.setAttribute("book", existingBook);
        dispatcher.forward(request, response);
 
    }
     private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
        Book newBook = new Book(title, author, price);
        DBUtils.insert(newBook);
        response.sendRedirect("list");
    }
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
        Book bookUpdate = new Book(id, title, author, price);
        DBUtils.update(bookUpdate);
        response.sendRedirect("list");
    }
 
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
        DBUtils.delete(id);
        response.sendRedirect("list");
 
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}