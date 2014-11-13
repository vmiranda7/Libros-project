package edu.upc.eetac.dsa.vmiranda.libros.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.vmiranda.libros.api.model.Resena;

@Path("/resena")
public class ResenaResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	private String GET_RESENA_BY_ID_QUERY = "select * from resena where idresena=?";

	@GET
	@Path("/{idresena}")
	@Produces(MediaType.LIBRO_API_RESENA)
	public Resena getResena(@PathParam("idresena") int idresena) {
		Resena resena = new Resena();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_RESENA_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(idresena));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resena.setIdresena(rs.getInt("idresena"));
				resena.setIdlibro(rs.getInt("idLibro"));
				resena.setCreador(rs.getString("creador"));
				resena.setDatos(rs.getString("datos"));
				resena.setFecha(rs.getString("fecha"));
			} else {
				throw new NotFoundException("There's no Reseña with idresena="
						+ idresena);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}

	private String DELETE_RESENA_QUERY = "DELETE  FROM resena where idresena=?";

	@DELETE
	@Path("/{idresena}")
	public void deleteResena(@PathParam("idresena") int idresena) {
		// validateUser(stingid);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_RESENA_QUERY);
			stmt.setInt(1, Integer.valueOf(idresena));

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no resena with idresena="
						+ idresena);// Deleting inexistent sting
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	private Resena getResenaFromDatabase(int idresena) {
		Resena resena = new Resena();
		Connection conn = null;
	
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_RESENA_BY_ID_QUERY);
			stmt.setInt(1, idresena);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				resena.setIdresena(rs.getInt("idresena"));
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setCreador(rs.getString("creador"));
				resena.setDatos(rs.getString("datos"));
				resena.setFecha(rs.getString("fecha"));
			} 
			else {
				throw new NotFoundException("There's no libro with idlibro="
						+ idresena);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}
	
private String INSERT_RESENA_QUERY="insert into resena (idlibro, creador, datos, fecha) values (?,?,?,?)";  
	
	@POST
	@Consumes(MediaType.LIBRO_API_RESENA)
	@Produces(MediaType.LIBRO_API_RESENA)
	public Resena createResena(Resena resena) {
		//validateLibro(libro);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_RESENA_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			//stmt.setString(1, security.getUserPrincipal().getName());
			stmt.setInt(1, resena.getIdlibro());
			stmt.setString(2, resena.getCreador());
			stmt.setString(3, resena.getDatos());
			stmt.setString(4, resena.getFecha());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int idresena = rs.getInt(1);

				resena = getResenaFromDatabase(idresena);
			} else {
				// Something has failed...
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}
	
	private String UPDATE_RESENA_QUERY="update resena set idlibro=ifnull(?, idlibro), datos=ifnull(?, datos), fecha=ifnull(?, fecha) where idresena=?";

	@PUT
	@Path("/{idresena}")
	@Consumes(MediaType.LIBRO_API_RESENA)
	@Produces(MediaType.LIBRO_API_RESENA)
	public Resena updateResena(@PathParam("idresena") int idresena, Resena resena) {
		//validateUser(stingid);
		//validateUpdateSting(sting);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_RESENA_QUERY);
			stmt.setInt(1, resena.getIdlibro());
			stmt.setString(2, resena.getDatos());
			//stmt.setString(3, resena.getCreador());
			stmt.setString(3, resena.getFecha());
			stmt.setInt(4, idresena);
			
			int rows = stmt.executeUpdate();
			if (rows == 1)
				resena = getResenaFromDatabase(idresena);
			else {
				throw new NotFoundException("There's no resena with idresena="
						+ idresena);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return resena;
	}
	
	

}
