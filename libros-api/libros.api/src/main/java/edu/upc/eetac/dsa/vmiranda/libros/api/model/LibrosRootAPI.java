package edu.upc.eetac.dsa.vmiranda.libros.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.vmiranda.libros.api.LibrosRootAPIResource;
import edu.upc.eetac.dsa.vmiranda.libros.api.MediaType;
import edu.upc.eetac.dsa.vmiranda.libros.api.LibrosResource;

public class LibrosRootAPI {
 //mirar bien que coño hace
	/*@InjectLinks({
		@InjectLink(resource = LibrosRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API", method = "getRootAPI"),
		@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "libros", title = "Latest stings", type = MediaType.LIBRO_API_LIBROS_COLLECTION),
		@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "create-libros", title = "Latest stings", type = MediaType.LIBRO_API_LIBROS) })
	*/
	private List<Link> links;
 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	
	
}
