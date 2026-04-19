import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrakseComponent } from './prakse-component';

describe('PrakseComponent', () => {
  let component: PrakseComponent;
  let fixture: ComponentFixture<PrakseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrakseComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PrakseComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
