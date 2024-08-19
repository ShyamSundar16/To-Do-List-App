import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http'; // Import withFetch
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { TaskComponent } from './components/task/task.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';


const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "task", component: TaskComponent },
  { path: '', redirectTo: "/login", pathMatch: 'full' },
  { path: '**', component: NotFoundComponent },

]
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NotFoundComponent,
    TaskComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes), ReactiveFormsModule,HttpClientModule
  ],
  providers: [
    provideClientHydration(),provideHttpClient(withFetch())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
