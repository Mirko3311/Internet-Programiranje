import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InternshipService
{
  private readonly  API_URL = `${environment.apiUrl}/internships`;

  constructor(private http:HttpClient)
  {}
  
  
  getMyInternships():Observable<any[]>
  {
    return this.http.get<any[]>(`${this.API_URL}/my-internships`,
      {
        withCredentials:true
      }
    );
  }
  getInternShipById(id:number):Observable<any>
  {
    return this.http.get(`${this.API_URL}/${id}`,
      {
        withCredentials:true
      }
    );
  }

  getAllInternships():Observable<any[]>
  {
    return this.http.get<any[]>(`${this.API_URL}/all`,
      {
        withCredentials:true
      }
    );
  }

  getUnappliedInternships(): Observable<any[]> {
  return this.http.get<any[]>(`${this.API_URL}/my-unapplied`,
    {
      withCredentials: true
    }
  );
}
    
}
