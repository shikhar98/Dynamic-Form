

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class form
 */
@WebServlet("/form")
public class form extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public form() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		out.println("<body bgcolor=white>");
		out.println("<h1>Create Your Account</h1>");
		out.println("<form method='post' action ='page'>");
		out.println("User ID: ");
		out.println("<input type='number' name='id'/>");
		out.println("UserName: ");
		out.println("<input type='text' name='user'/>");
		out.println("Password :");
		out.println("<input type='password' name='password'/><br><br>");
		Connection con = getConnection();
		String sql = "select * from skills";
		try{
			Statement stmt = con.createStatement();
			ResultSet  rs = stmt.executeQuery(sql);
			if(!rs.wasNull()){
				out.print("<label>Skill:</label>");
				while(rs.next()){
					out.print("<input type='checkbox' name='skill' value='"+rs.getInt("sid")+"'>"+rs.getString("sname")+"&nbsp;");
				}
			}
			sql="select * from qualification";
			rs=stmt.executeQuery(sql);
			if(!rs.wasNull()){
				out.println("<br><br><br><label>Qualifications: </label>");
				while(rs.next()){
					out.println("<input type='checkbox' name='qual' value='"+rs.getInt("qid")+"'>"+rs.getString("qname")+"&nbsp;");
				}
			} 
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		out.println("<br><br><input type='submit' value='Submit' style='font-size : 20px; width: 100px; height: 50px;'/>");
		out.println("</form>");
		out.println("<form action='update'>");
		out.println("<input type='submit' value='All Users'  style='font-size : 20px; width: 100px; height: 50px;'>");
		out.println("</form>");
		out.println("</body>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		out.println("<body bgcolor=cyan>");
		String df=request.getParameter("id");
		int id=Integer.parseInt(df);
		String nam=request.getParameter("user");
		String pass=request.getParameter("password");
		String []skill=request.getParameterValues("skill");
		String []qual=request.getParameterValues("qual");
		int s[]=new int[skill.length];
		for(int i=0;i<skill.length;i++){
			s[i]=Integer.parseInt(skill[i]);
		}
		int q[]=new int[qual.length];
		for(int i=0;i<q.length;i++){
			q[i]=Integer.parseInt(qual[i]);
		}
		
		try{
		Connection con = getConnection();
		Statement stmt=con.createStatement();
		stmt.execute("create table if not exists login(id int primary key,name varchar(20),password varchar(20))");
		stmt.execute("insert into login values('"+id+"','"+nam+"','"+pass+"')");
		for(int i=0;i<skill.length;i++){
			stmt.execute("insert into skusr (uid,sid) values('"+id+"','"+skill[i]+"')");
		 }
		for(int i=0;i<qual.length;i++){
			stmt.execute("insert into qualusr (uid,qid) values('"+id+"','"+qual[i]+"')");
		 }
		out.println("Data Inserted");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private Connection getConnection(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/shikhar","shikhar","user");
		}catch(Exception e){
			
		}
		return con;
	}

}