import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaPraksi } from './lista-praksi';

describe('ListaPraksi', () => {
  let component: ListaPraksi;
  let fixture: ComponentFixture<ListaPraksi>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaPraksi],
    }).compileComponents();

    fixture = TestBed.createComponent(ListaPraksi);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
