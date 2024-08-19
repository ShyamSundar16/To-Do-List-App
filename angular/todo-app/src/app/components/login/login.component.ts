import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthUser } from '../../models/AuthUser';
import { User } from '../../models/User';
import { AuthUserService } from '../../services/auth.user.service';
import { UserService } from '../../services/user.service';
import { Token } from '@angular/compiler';
import { Role } from '../../models/Role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  signupForm: FormGroup
  loginForm: FormGroup

  constructor(private userService: UserService,private router: Router, private authService: AuthUserService) {
    this.signupForm = new FormGroup({
      name: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.required, Validators.email]),
      phone: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required]),
    })
    this.loginForm = new FormGroup({
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required])

    })
   }

  ngOnInit(): void {
  }

  getLogin() {
    let u: User = this.loginForm.value;
    let authUser: AuthUser = new AuthUser;
    authUser.username = u.email;
    authUser.password = u.password
    this.validateUserAndNavigate(authUser);
  }

  signUpUser() {
    let userObj: User = this.signupForm.value
    userObj.id = userObj.email;
    userObj.username = userObj.email;
    userObj.role = "customer";
    this.userService.saveUser(userObj)
      .subscribe((res:any) => {
        userObj.valid = true;
        this.userService.user = userObj;
        alert("Registered Successfully, Kindly login to proceed.")
        this.router.navigate(["login"]);
      });
  }

  validateUserAndNavigate(user: AuthUser) {
    this.authService.authenticateUser(user).
      subscribe((res: any) => {

        let token: string = JSON.stringify(res)
        //let decoded: Token = jwt_decode(token);
        let u: User = new User;
        u.valid = true;
        u.email = user.username;
        this.userService.user = u;
        this.router.navigate(["task"])
        sessionStorage.setItem("userid", user.username);
      });

  }

}
