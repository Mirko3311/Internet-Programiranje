package net.etf.ip.beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.etf.ip.models.ApplicationResponse;
import net.etf.ip.models.CVResponse;
import net.etf.ip.service.GenericService; // Putanja do tvog GenericService-a

public class ApplicationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private GenericService<ApplicationResponse> service = new GenericService<>();
    private List<ApplicationResponse> applications = new ArrayList<>();

    public ApplicationBean() {}
    private Integer selectedId;
    private CVResponse currentCV;
    
    
    public Integer getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Integer selectedId) {
		this.selectedId = selectedId;
	}

	public CVResponse getCurrentCV() {
		return currentCV;
	}

	public void setCurrentCV(CVResponse currentCV) {
		this.currentCV = currentCV;
	}

	public CVResponse getDetailedCV() {
        if (selectedId == null) return null;
        try {
        	
            this.currentCV = service.get("/cv/" + selectedId, CVResponse.class);
            System.out.println("Selected ID: " + selectedId);
            return currentCV;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void fetchApplications() {
        try {
            this.applications = service.getList("/application/company-applications", ApplicationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            this.applications = new ArrayList<>();
        }
    }
    
    private String searchQuery; 

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    
    private String filterStatus = ""; 

    public void setFilterStatus(String status) {
        this.filterStatus = (status == null) ? "" : status;
    }

  /*  public List<ApplicationResponse> getApplications() {
        if (applications == null || applications.isEmpty() || filterStatus.isEmpty()) {
            fetchApplications();
        }
        return applications.stream()
                .filter(a -> (filterStatus == null || filterStatus.isEmpty() || a.getStatus().equals(filterStatus)))
                .filter(a -> (searchQuery == null || searchQuery.isEmpty() || 
                              a.getStudentFullName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                              a.getInternshipTitle().toLowerCase().contains(searchQuery.toLowerCase())))
                .collect(Collectors.toList());
        }*/
    
    public List<ApplicationResponse> getApplications() {
        if (applications == null || applications.isEmpty()) {
            fetchApplications();
        }
        if (applications == null) return new ArrayList<>();
        return applications.stream()
                .filter(a -> (filterStatus == null || filterStatus.isEmpty() || a.getStatus().equals(filterStatus)))
                .filter(a -> (searchQuery == null || searchQuery.isEmpty() || 
                              a.getStudentFullName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                              a.getInternshipTitle().toLowerCase().contains(searchQuery.toLowerCase())))
                .collect(Collectors.toList());
    }
    public void setApplications(List<ApplicationResponse> applications) {
        this.applications = applications;
    }
    
    public boolean updateStatus(Integer applicationId, String newStatus) {
        try {
            String endpoint = "/application/" + applicationId + "/status?status=" + newStatus;

            Boolean success = service.put(endpoint, null, Boolean.class);
            return success != null && success;
        } catch (Exception e) {
            System.err.println("Greška pri pozivu backend-a: " + e.getMessage());
            return false;
        }
    }
}