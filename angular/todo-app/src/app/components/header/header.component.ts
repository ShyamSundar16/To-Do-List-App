import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {


  constructor(private userService: UserService){
  }
  getUserSession(){
    return this.userService.user.valid;
   }
   clearSession(){
     sessionStorage.clear();
     this.userService.user.valid=false;
   }
}
