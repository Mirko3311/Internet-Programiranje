import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RecommendationService } from '../../services/recommendation';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSpinner } from '@angular/material/progress-spinner';
import { RouterModule } from '@angular/router';
import { CommonModule, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-recommendation',
  imports: [MatCardModule,MatIconModule,RouterModule, MatCardModule, CommonModule, DecimalPipe, MatSpinner],
  templateUrl: './recommendation.html',
  styleUrl: './recommendation.css',
})



export class Recommendation implements OnInit {
  
  // Niz u koji ćemo smjestiti preporuke sa bekenda
  recommendations: any[] = [];
  isLoading: boolean = false;

  constructor(private recommendationService: RecommendationService, private cdr:ChangeDetectorRef) { }

  ngOnInit(): void {
    this.loadRecommendations();
  }

  loadRecommendations() {
    this.isLoading = true;
    this.recommendationService.getMyRecommendations().subscribe({
      next: (data) => {
        this.recommendations = data;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Greška pri učitavanju preporuka', err);
        this.isLoading = false;
      }
    });
  }

 
  getScoreColor(score: number): string {
    if (score >= 0.8) return '#4caf50'; 
    if (score >= 0.5) return '#ff9800';
    return '#f44336'; 
  }}