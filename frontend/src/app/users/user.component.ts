import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {LoginService} from "../login/login.service";

@Component({
  template: "Welcome {{getName()}}"
})
export class UserComponent {
  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService
  ){}

  getName(): string {
      return this.loginService.getName();
  }
}
