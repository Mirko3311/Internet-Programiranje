import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { Login } from './components/login/login';
import { CvComponent } from './components/cv-component/cv-component';
import { PrakseComponent } from './components/prakse-component/prakse-component';
import { DetaljiPrakseComponent } from './components/detalji-prakse-component/detalji-prakse-component';
import { ListaPraksi } from './components/dnevnik-rada/lista-praksi/lista-praksi';
import { DetaljiDnevnika } from './components/dnevnik-rada/detalji-dnevnika/detalji-dnevnika';
import { Recomendation } from './models/recommendation/recomendation';
import { Recommendation } from './components/recommendation/recommendation';

export const routes: Routes = [
    { path: 'login',component: Login },
    {  path: 'dashboard',component: Dashboard,
        children:
        [
            {path:'cv', component: CvComponent, data: {title: "Moj CV"}},
            {path: 'prakse', component: PrakseComponent, data: {title: " Prakse"}},
            {path: 'prakse/:id',component: DetaljiPrakseComponent},
            { path:'dnevnik', component: ListaPraksi},
            { path:'work-log/:id',component:DetaljiDnevnika},
            { path: 'preporuke', component: Recommendation}
        ]
    },
    {
        path:'**',redirectTo:'login'
    },
];