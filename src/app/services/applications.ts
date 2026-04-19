import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root',})
export class ApplicationsService 
{
  private readonly API_URL = `${environment.apiUrl}/applications`;

  constructor(private http:HttpClient)
  {}

  applyToInternship(internshipId: number): Observable<void> {
    const url = `${this.API_URL}/apply/${internshipId}`;
    
    return this.http.post<void>(url, {}, { withCredentials: true });
  }

  getMyApplications(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/all`, { withCredentials: true });
  }

}