import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatUnitComponent } from './chat-unit.component';

describe('ChatUnitComponent', () => {
  let component: ChatUnitComponent;
  let fixture: ComponentFixture<ChatUnitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatUnitComponent]
    });
    fixture = TestBed.createComponent(ChatUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
