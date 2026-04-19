import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatAnchor } from "@angular/material/button";
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { WorkLogService } from '../../../services/work-log';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalji-dnevnika',
  imports: [MatExpansionModule,FormsModule, MatInputModule ,MatAnchor,CommonModule,MatAnchor,MatFormFieldModule, MatIconModule],
  templateUrl: './detalji-dnevnika.html',
  standalone: true,
  styleUrl: './detalji-dnevnika.css',
})
export class DetaljiDnevnika implements OnInit {

  zapisi:any[] = [];
  internshipId! : number;
  constructor(private route: ActivatedRoute, private workLogService: WorkLogService, private cdr:ChangeDetectorRef)
  {}
  dodajNoviZapis()
  {
    const noviLog =
    {
      weekNumber: this.zapisi.length+1,
      description: '',
      isEditing: true
    };
    this.zapisi.unshift(noviLog);
  } 

  spasiZapis(zapis:any)
  {
    if(!zapis.description || zapis.description.trim() =='')
    {
      alert("Unesite opis aktivnosti!");
      return;
    }

    if(zapis.id)
    {
      this.workLogService.update(zapis.id, zapis).subscribe({
      next: (res) => {
        alert("Izmjene sačuvane!");
        zapis.isEditing = false;
      },
      error: (err) => console.log(err)
    });
    }else
    {
    this.workLogService.create(this.internshipId,zapis).subscribe
  ({
    next:(response: any) =>
    {
      alert("Uspjesno sacuvano u bazu!");
      if(response && response.id)
      {
        zapis.id = response.id;
      }

      zapis.isEditing = false;
    },
    error: (err) => console.log(err)
  });
  }
}


  obrisiZapis(zapis:any)
  {

  }
  ngOnInit()
  {
   /* const idParam = this.route.snapshot.paramMap.get('id');
    if(idParam)
    {
      this.internshipId = Number(idParam);
      this.ucitajZapise();
    }*/
   this.route.paramMap.subscribe(params =>
   {
     this.internshipId = Number(params.get('id'));
     console.log("ID iz URL-a: ", this.internshipId);
     this.ucitajZapise();
   });
  }

    ucitajZapise()
    {
    this.workLogService.getAll(this.internshipId).subscribe(data =>
    {
        this.zapisi = data.map(z => ({...z, isEditing: false}));
        this.cdr.detectChanges();
    }); 
   }

}
