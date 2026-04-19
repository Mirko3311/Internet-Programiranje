import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
    private readonly  API_URL = `${environment.apiUrl}/recommendations`;
 
  constructor(private http: HttpClient) { }

  getMyRecommendations(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/my-recommendations`,
        {
            withCredentials:true
        }
    );
  }
}