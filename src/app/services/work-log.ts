import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WorkLogService
{
  private readonly API_URL = `${environment.apiUrl}`;

  constructor(private http: HttpClient)
  {

  }

  create(internshipId: number, data: any)
  {
    return this.http.post(`${this.API_URL}/work-log/create/${internshipId}`,data,
      {
        withCredentials:true
      }
    )
  }

  update(logId: number, data: any) {
  return this.http.put<any>(`${this.API_URL}/work-log/update/${logId}`, data, { withCredentials: true });
}
  getAll(internshipId:number)
  {
    return this.http.get<any[]>(`${this.API_URL}/work-log/${internshipId}`,
      {
        withCredentials:true
      }
    );
  }

  getMyInternships():Observable<any[]>
  {
    return this.http.get<any[]>(`${this.API_URL}/internships/my-internships`,
    {
       withCredentials:true
    });
  }
}
