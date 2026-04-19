import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InternshipService } from '../../services/internship';
import {MatGridListModule} from '@angular/material/grid-list';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipSet, MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { ApplicationsService } from '../../services/applications';
@Component({
  selector: 'app-detalji-prakse-component',
  imports: [MatGridListModule, MatIconModule, MatCardModule, MatChipsModule ,CommonModule],
  templateUrl: './detalji-prakse-component.html',
  styleUrl: './detalji-prakse-component.css',
})
export class DetaljiPrakseComponent implements OnInit
 {
  praksa:any;

  constructor(
    private route:ActivatedRoute,
    private service: InternshipService,
    private applicationsService: ApplicationsService,
    private router: Router,
    private cdr: ChangeDetectorRef)
  {}

  ngOnInit(): void {
   console.log('Stranica se ucitala, krecem po podatke...');
   const id = Number(this.route.snapshot.paramMap.get('id'));
   
   this.service.getInternShipById(id).subscribe(data =>
   {
      console.log('Podaci stigli u komponentu:', data);
       this.praksa = data;
       this.cdr.detectChanges();
   });
  }

  prijaviSe(internshipId: number): void {
    this.applicationsService.applyToInternship(internshipId).subscribe({
      next: () => {
        alert('Uspješno ste se prijavili na praksu!');
        this.router.navigate(['/dashboard/prakse']);
      },
      error: (err) => {
        console.error('Greška pri prijavi:', err);
        alert('Došlo je do greške prilikom prijave. Možda ste se već prijavili?');
      }
    });
  }
 }
