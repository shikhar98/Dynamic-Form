

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class edith
 */
@WebServlet("/edith")
public class edith extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public edith() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		try
		{
		out.println("<body>");
		out.println("<form action='edith' method='post'>");
		String id=request.getParameter("id");
		out.print(id);
		Connection con = getConnection();
		String sql= "select * from login where id="+id;
		String sql1="select sid from skusr where uid="+id;
		String sql2="select * from skills";
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		out.println("<input type='hidden' name='id' value='"+id+"'>");
		out.println("<label>UserName: </label>");
		out.println("<input type='text' name ='user' value='"+rs.getString("name")+"'>");
		ResultSet rs1 = stmt.executeQuery(sql1);
		ArrayList<Integer> sarr = new ArrayList<Integer>();
		if(!rs1.wasNull()){
			while(rs1.next()){
				sarr.add(rs1.getInt("sid"));
			}
		}
		out.println("<br><br><label>Skills: </label>");
		ResultSet rs2 = stmt.executeQuery(sql2);
		while(rs2.next()){
			String check = "";
			  if(sarr.indexOf(rs2.getObject("sid"))>-1){
				  check = "checked='checked'";
			  }
			out.print("<input type='checkbox' name='skill' "+check+" value='"+rs2.getInt("sid")+"'>"+rs2.getString("sname")+"&nbsp;");	
		}
		String sql4="select qid from qualusr where uid='"+id+"'";
		String sql5="select * from qualification";
		out.println("<br><br><label>Qualification:</label>");
		ResultSet rs3=stmt.executeQuery(sql4);
		ArrayList<Integer> qarr=new ArrayList<Integer>();
		if(!rs3.wasNull()){
			while(rs3.next()){
				qarr.add(rs3.getInt("qid"));
			}
		}
		ResultSet rs4=stmt.executeQuery(sql5);
		while(rs4.next()){
			String check2="";
			if(qarr.indexOf(rs4.getObject("qid"))>-1){
				check2="checked='checked'";
			}
			out.println("<input type='checkbox' name='qual' "+check2+" value ='"+rs4.getInt("qid")+"'>"+rs4.getString("qname")+"&nbsp;");
		}
		out.println("<br><br><input type='Submit'>");
		out.println("</body>");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			Connection con=getConnection();
			Statement stmt=con.createStatement();
			String df=request.getParameter("id");
			String []skill1=request.getParameterValues("skill");	
			String []qual1=request.getParameterValues("qual");
			int skill[]=new int[skill1.length];        
			for(int i=0;i<skill.length;i++){
				skill[i]=Integer.parseInt(skill1[i]);
			}
			int qual[]=new int[qual1.length];
			for(int i=0;i<qual.length;i++){
				qual[i]=Integer.parseInt(qual1[i]);
			}
			int id=Integer.parseInt(df);
			String user=request.getParameter("user");
			stmt.execute("update login set name='"+user+"' where id='"+id+"'");
			stmt.executeUpdate("delete from skusr where uid = '"+id+"'");
			stmt.executeUpdate("delete from qualusr where uid = '"+id+"'");
			for(int i=0;i<skill.length;i++){
				stmt.execute("insert into skusr(uid,sid) values ('"+id+"','"+skill[i]+"')");
			}
			for(int i=0;i<qual.length;i++){
				stmt.execute("insert into qualusr(uid,qid) values('"+id+"','"+qual[i]+"')");
			}
            response.sendRedirect("./update");
		}catch(Exception e){
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
