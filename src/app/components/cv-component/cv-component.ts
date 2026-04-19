import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatStepperModule } from '@angular/material/stepper';

import { inject} from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule, FormGroup, FormArray} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { CvService } from '../../services/cv';
import { CommonModule } from '@angular/common';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatChipGrid, MatChipRow, MatChipInput } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatDivider } from "@angular/material/divider";
import { MatToolbar, MatToolbarModule } from "@angular/material/toolbar";
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
@Component({
  selector: 'app-cv-component',
  imports: [MatStepperModule, MatFormFieldModule,
    MatButtonModule,  
    MatStepperModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule, CommonModule, MatChipGrid, MatChipRow, MatChipInput, MatToolbarModule,MatToolbar],
  templateUrl: './cv-component.html',
  styleUrl: './cv-component.css',
})
export class CvComponent implements OnInit
{
  cvPostoji: boolean = false;
  cvForm!: FormGroup; 
  isLiner = true;
  isUpdateMode = false;
  cvId: number | null = null;
  fileName='';
  imagePreview : string | ArrayBuffer | null = null;
  skillNames:string[] = [];
  readonly separatorKeysCodes = [ENTER,COMMA] as const;
  constructor(private fb: FormBuilder, private cvService: CvService, private cdr:ChangeDetectorRef)
  {}

removeImage(event: Event) {
  event.stopPropagation(); 
  this.imagePreview = null;
}
  popuniFormu(data:any)
  {
     this.cvForm.patchValue({
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      phoneNumber: data.phoneNumber,
      summary: data.summary,
      interests: data.interests || ""
     });

     this.skillNames = data.skillNames || [];

     const workArray = this.cvForm.get('workExperiences') as FormArray;

    if (data.workExperiences && data.workExperiences.length > 0)
    {
    data.workExperiences.forEach((work: any) => {
      workArray.push(this.fb.group({
        employer: [work.employer],
        position: [work.position],
        city: [work.city],
        period: [work.period]
      }));
    });
  }
    else
    {
     
    }
    
    const eduArray = this.cvForm.get('educations') as FormArray;

    eduArray.clear();
    if(data.educations && data.educations.length >0)
    {
      data.educations.forEach((edu:any) => {
        eduArray.push(this.fb.group({
          institution: [edu.institution],
          degree: [edu.degree],
          fieldOfStudy:[edu.fieldOfStudy],
          period: [edu.period]
        }));
      });
    } else
    {
      this.addEducation();
    }
    const internArray = this.cvForm.get('internships') as FormArray;
  internArray.clear();
  if (data.internships && data.internships.length > 0) {
    data.internships.forEach((intern: any) => {
      internArray.push(this.fb.group({
        companyName: [intern.companyName],
        position: [intern.position],
        period: [intern.period],
        description: [intern.description]
      }));
    });
  } else {
  
  }

    if(data.imagePath)
    {
      this.imagePreview = data.imagePath;
      this.cvForm.patchValue({ photoPath: data.imagePath });
    }
  }

exportToPDF() {
  const data = document.querySelector('.a4-paper') as HTMLElement;
  if (!data) {
    console.error('Element .a4-paper nije pronađen!');
    return;
  }
  const originalTransform = data.style.transform;
  data.style.transform = 'none';

  html2canvas(data, {
    scale: 2,
    useCORS: true,
    logging: false,
    backgroundColor: '#ffffff',
    windowWidth: data.scrollWidth,
    windowHeight: data.scrollHeight
  }).then(canvas => {
    data.style.transform = originalTransform;

    const imgWidth = 210;
    const imgHeight = (canvas.height * imgWidth) / canvas.width;
    const contentDataURL = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4'); 
    pdf.addImage(contentDataURL, 'PNG', 0, 0, imgWidth, imgHeight);
    pdf.save(`CV_${this.cvForm.value.firstName || 'korisnik'}.pdf`);
  });
}
  obrisiCV()
  {
    const potvrda = window.confirm("Da li ste sigurni da zelite trajno obrisati svoj CV?");

    if(potvrda)
    {
      this.cvService.deleteCV().subscribe({
          next: () =>
          {
            this.cvForm.reset();

            this.educations.clear();
            this.workExperiences.clear();

            this.addEducation();
            this.addWorkExperience();
            this.cvPostoji = false;

            this.cvId=null;
            this.isUpdateMode = false;
            this.cvPostoji = false;
            this.imagePreview = null;
            this.skillNames = [];
            this.fileName = '';
            alert("CV je uspjesno obrisan.");
          },
          error: (err) =>
          {
            console.error("Greska pri brisanju:",err);
            alert("Doslo je do greske prilikom brisanja.");
          }
      });
    }
  }
  ucitajPostojeciCV()
  {
    this.cvService.getMojCV().subscribe({
      next: (data) =>
      {
        if(data)
        {
          this.isUpdateMode = true;
          this.cvId = data.id;
          this.popuniFormu(data);
        }
      },
      error : (err) => console.log("Korisnik nema CV")
    });
  }
  dodajVjestinu(event:any):void
  {
    const value = (event.value || '').trim();

    if(value && !this.skillNames.includes(value))
    {
      this.skillNames.push(value);
    }
    if(event.chipInput)
    {
      event.chipInput.clear();
    }
   }

 ukloniVjestinu(skill: string): void {
  const index = this.skillNames.indexOf(skill);
  if (index >= 0) {
    this.skillNames.splice(index, 1);
  }
}

  ngOnInit(): void {
    this.cvForm = this.fb.group({
    //studentId: [1], 
    firstName: ['', Validators.required],
    lastName:['',Validators.required], 
    email:['',[Validators.required, Validators.email]],
    summary:['',Validators.required],
    photoPath: [''],
    phoneNumber:[''],
    educations: this.fb.array([]),
    workExperiences: this.fb.array([]),
    interests:[''],
    internships:this.fb.array([])
  });
   this.ucitajPodatkeSaServera();
  }


  private ucitajPodatkeSaServera()
  {
    this.cvService.getMojCV().subscribe({
      next:(existingCv) =>
      {
        if(existingCv)
        {
          this.isUpdateMode = true;
          this.cvId = existingCv.id;
          this.popuniFormu(existingCv);
        }else
        {
          this.addEducation();
          this.addWorkExperience();
          this.addInternship();
        }
      }, error: (err) =>
      {
        console.error("Greska ili CV ne postoji!",err);
        this.addEducation();
        this.addWorkExperience();
        this.addInternship();
      }
    })
  }


  get educations()
  {
    return this.cvForm.get('educations') as FormArray;
  }

  get internships() {
  return this.cvForm.get('internships') as FormArray;
}
  addInternship()
  {
    const control = this.cvForm.get('internships') as FormArray;
    control.push(this.fb.group({
    companyName: [''],
    position: [''],
    period: [''],
    description: ['']
    }));
  }

  addEducation()
  {
    this.educations.push(this.fb.group({
      institution: ['', Validators.required],
      degree:['',Validators.required],
      fieldOfStudy:['',Validators.required],
      period:['']
    }));
  }

  addWorkExperience():void
  {
    this.workExperiences.push(this.fb.group({
      employer: ['', Validators.required],
      position:['', Validators.required],
      city:[''],
      period:[''],
      mainActivities:['']
    }));
  }

  get workExperiences():FormArray
  {
    return this.cvForm.get('workExperiences') as FormArray;
  }
  onSubmit()
  {
   
    if(this.cvForm.valid)
    {
      const dataToSend = this.cvForm.value;

      const cvData = 
      {
        ...dataToSend,
        skillNames: this.skillNames
      };
      console.log('Podaci koji idu na backend:',cvData);

      if(this.isUpdateMode && this.cvId)
      {

        this.cvService.update(cvData).subscribe({
          next:(response) =>
          {
            alert("CV uspjesno azuriran!");
            console.log("Update odgovor:",response)
          },
          error: (err) => alert('Greska prilikom azuriranja!')
        });

      }
      else
      {       
      this.cvService.create(cvData).subscribe({
        next: (response) =>
        {
          alert("CV uspjesno sacuvan");
          this.isUpdateMode = true;
          this.cvId = response.id;
        },
        error: (err) =>
        {
          alert('Greska prilikom cuvanja!');
          console.error(err);
        }
      });
     }
    } else
    {
      this.cvForm.markAllAsTouched();
      alert("Popunite sva polja");
    }
  }
  
  removeEducation(index: number)
  {
    if(this.educations.length>1)
    {
      this.educations.removeAt(index);
    }else
    {
      alert("Morate imati barem jedno navedeno obrazovanje");
    }
  }

  removeInternship(index:number)
  {
    if(this.internships.length>1)
    {
      this.internships.removeAt(index);
    }else
    {
      alert("Morate imati bar jednu praksu!");
    }
  }

  removeWorkExperience(index: number)
  {
    this.workExperiences.removeAt(index);
  }

  onFileSelected(event:any)
  {
    const file:File = event.target.files[0];
    if(file)
    {
      this.processFile(file);
    }
  }

  onFileDropped(event: DragEvent)
  {
    event.preventDefault();
    const file=event.dataTransfer?.files[0];
    if(file)
    {
      this.processFile(file);
    }
  }

  onDragOver(event:DragEvent)
  {
    event.preventDefault();
  }


private processFile(file: File): void {
  this.fileName = file.name;

  const reader = new FileReader();
  reader.onload = () => {
    const result = reader.result;
    this.imagePreview = result;

    this.cvForm.patchValue({
      photoPath: result
    });
  };

  reader.readAsDataURL(file);
}

}
