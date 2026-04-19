import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftSideBar } from './left-side-bar';

describe('LeftSideBar', () => {
  let component: LeftSideBar;
  let fixture: ComponentFixture<LeftSideBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeftSideBar],
    }).compileComponents();

    fixture = TestBed.createComponent(LeftSideBar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
