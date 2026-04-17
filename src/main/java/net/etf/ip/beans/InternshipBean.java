package net.etf.ip.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.etf.ip.models.InternshipRequest;
import net.etf.ip.models.InternshipResponse;
import net.etf.ip.models.StudentResponse;
import net.etf.ip.service.GenericService;

public class InternshipBean implements Serializable
{
  private List<InternshipResponse> internships = new ArrayList<>();
  private GenericService<InternshipResponse> service = new GenericService<>();
  private Integer internshipId;
  private String query;
  
  
  
  private String sort = "title"; 
  private String order = "asc";  

  public void setSort(String sort) { this.sort = sort; }
  public void setOrder(String order) { this.order = order; }


  public String getSort() { return sort; }
  public String getOrder() { return order; }
  public String getQuery() { return query; }

  public void setQuery(String query) {
	this.query = query;
  }

  public List<InternshipResponse> search(String query) {
	    List<InternshipResponse> svePrakse = getAllInternships(); 

	    return svePrakse.stream()
	        .filter(p -> p.getTitle().toLowerCase().contains(query.toLowerCase()) || 
	                     p.getDescription().toLowerCase().contains(query.toLowerCase()))
	        .collect(Collectors.toList());
	}
  
  public List<InternshipResponse> getInternships() {
	    if (query != null && !query.trim().isEmpty()) {
	        this.internships = search(query); 
	    } else {
	
	        this.internships = getAllInternships();
	    }
	    if (this.internships != null && sort != null) {
	        this.internships.sort((a, b) -> {
	            int cmp = 0;
	            switch (sort) {
	                case "title":
	                    cmp = a.getTitle().compareToIgnoreCase(b.getTitle());
	                    break;
	                case "companyName":
	                    cmp = a.getCompanyName().compareToIgnoreCase(b.getCompanyName());
	                    break;
	                case "startDate":
	                    cmp = a.getStartDate().compareTo(b.getStartDate());
	                    break;
	            }
	            return "desc".equals(order) ? -cmp : cmp;
	        });
	    }
	    return this.internships;
	}
  


  public void setInternships(List<InternshipResponse> internships) {
	this.internships = internships;
  }

  public Integer getInternshipId() {
	return internshipId;
  }

  public void setInternshipId(Integer internshipId) {
	this.internshipId = internshipId;
  }

  public List<InternshipResponse> getAllInternships()
  {
	  try
	  {
		  this.internships = service.getList("/internships/all", InternshipResponse.class);
		  
	  }catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return internships;
  }
  

  public List<InternshipResponse> getMyInternships() {
	    try {
	    
	        return service.getList("/internships/company-internships", InternshipResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
  
  
  
  public boolean createinternship(InternshipRequest newInternship)
  {
	  try
	  {
	  service.post("/internships/create",newInternship,InternshipResponse.class);
	  this.getAllInternships();
	  System.out.println("Podaci poslati na backend: " + newInternship);
	  return true;
	  }catch(Exception e)
	  {
		  e.printStackTrace();
		  return false;
	  }
  }
  
  public List<StudentResponse> getStudentsForInternship() {
	    try {
	    	GenericService<StudentResponse> studentService = new GenericService<>();
	        String endpoint = "/internships/" + internshipId + "/students";
	        return studentService.getList(endpoint, StudentResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
  
  public boolean deleteInternship(Integer id)
  {
	  System.out.println("Pokrecem brisanje u metodi deleteInternship....");
	  boolean success = service.delete("/internships/" + id);
	  if(success)
	  {
		  getAllInternships();
	  }
	  return success;
   }
  
  public boolean updateInternship(Integer id, InternshipRequest updatedData) {
	    try {
	    	System.out.println(updatedData);
	        service.put("/internships/" + id, updatedData, InternshipResponse.class);
	        return true;
	    } catch (Exception e) {
	        System.err.println("Greška u InternshipBean (update): " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
}
