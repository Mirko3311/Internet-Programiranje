import { CommonModule } from '@angular/common';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipSet } from '@angular/material/chips';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { Router } from '@angular/router';
import { InternshipService } from '../../services/internship';

@Component({
  selector: 'app-prakse-component',
  imports: [CommonModule, MatTabsModule , MatChipSet ,MatSortModule, MatIconModule ,MatTableModule,MatCardModule, MatInputModule, MatPaginatorModule,MatFormFieldModule,MatFormFieldModule],
  templateUrl: './prakse-component.html',
  styleUrl: './prakse-component.css',
})
export class PrakseComponent implements OnInit, AfterViewInit 
{
  displayedColumns: string[] = ['kompanija','tehnologija','detaljno'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort)sort!: MatSort;
  dataSource = new MatTableDataSource<any>([]);
  internships: any[] = [];
  constructor(private dialog: MatDialog, private router: Router, private internshipService:InternshipService,private cdr: ChangeDetectorRef)
  {}
    ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
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
    this.loadMyInternship(); 
    this.loadAllInternship();  
  }

  loadAllInternship(): void {
    this.internshipService.getUnappliedInternships().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error("Greška pri dobavljanju svih praksi:", err);
      }
    });
  }

  loadMyInternship(): void {
    this.internshipService.getMyInternships().subscribe({
      next: (data) => {
        this.internships = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Greška pri dobavljanju mojih prijava:', err);
      }
    });
  }
  onSearch(event:Event)
  {
    const searchValue = (event.target as HTMLInputElement).value
    this.dataSource.filter = searchValue.trim().toLocaleLowerCase();
  }

  pogledajDetalje(id:number)
  {
    this.router.navigate(['/dashboard/prakse',id]);
  }
}
