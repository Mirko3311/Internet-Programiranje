import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetaljiDnevnika } from './detalji-dnevnika';

describe('DetaljiDnevnika', () => {
  let component: DetaljiDnevnika;
  let fixture: ComponentFixture<DetaljiDnevnika>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetaljiDnevnika],
    }).compileComponents();

    fixture = TestBed.createComponent(DetaljiDnevnika);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
