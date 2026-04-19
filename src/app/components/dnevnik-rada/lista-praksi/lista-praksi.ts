import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { WorkLogService } from '../../../services/work-log';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatChipSet, MatChipsModule } from "@angular/material/chips";

@Component({
  selector: 'app-lista-praksi',
  standalone:true,
  imports: [CommonModule, MatCardModule, MatChipsModule, MatIconModule, RouterModule, MatChipSet],
  templateUrl: './lista-praksi.html',
  styleUrl: './lista-praksi.css',
})
export class ListaPraksi implements OnInit
 {
   internships: any[] = [];
   constructor(private workLogService: WorkLogService,
    private router:Router, private cdr: ChangeDetectorRef
   )
   {}

   private skillColorMap: {[key:string]:string} = 
  {
    'Java':'#f44336',
    'SQL': '#2196f3',
    'Angular': '#9c27b0',
    'Python':'#1bf10b',
  }

  private defaultSkillColor='#757575';


  getSkillColor(skillName:string):string
  {
    if (!skillName) {
    return this.defaultSkillColor;
  }
    return this.skillColorMap[skillName] || this.defaultSkillColor;
  }

   ngOnInit(): void {
   const userData = localStorage.getItem('user');

   if(userData)
   {
    const user = JSON.parse(userData);
    this.workLogService.getMyInternships().subscribe({
      next: (data) =>
      {
        this.internships = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
   } else {
    this.router.navigate(['/login']);
  }
   }

   goToDnevnik(id: number)
   {
    this.router.navigate(['dashboard/work-log',id]);
   }

 }
