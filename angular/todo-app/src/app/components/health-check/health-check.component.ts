import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-health-check',
  template: '<p>OK</p>',
  styles: []
})
export class HealthCheckComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
  }
}
