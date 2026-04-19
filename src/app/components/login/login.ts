import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Route, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, MatCardModule, MatFormFieldModule, MatInputModule,MatButtonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login 
{
  loginData = { username: '', password: '' };

  constructor(private auth: AuthService,
    private router: Router) 
  { }
  
  onLogin()
  {
    console.log('Pokusaj prijave sa: ' + this.loginData.username);

    this.auth.login(this.loginData).subscribe
  ({
    next: (response) =>
    {
      console.log('Server odgovorio sa: ',response);
      localStorage.setItem('user',JSON.stringify(response));
      this.router.navigate(['/dashboard']);
    },
    error: (err) =>
    {
      alert("Neuspjesna prijava! Provjerite podatke.");
      console.error(err);
    }
  });
  }
}
