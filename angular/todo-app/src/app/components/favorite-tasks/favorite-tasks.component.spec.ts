import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteTasksComponent } from './favorite-tasks.component';

describe('FavoriteTasksComponent', () => {
  let component: FavoriteTasksComponent;
  let fixture: ComponentFixture<FavoriteTasksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FavoriteTasksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavoriteTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
