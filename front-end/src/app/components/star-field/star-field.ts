import { Component, OnInit } from '@angular/core';

interface Star {
  id: number;
  x: number;
  y: number;
  size: number;
  opacity: number;
  delay: number;
  duration: number;
}

@Component({
  selector: 'app-star-field',
  imports: [],
  templateUrl: './star-field.html',
  styleUrl: './star-field.scss',
})
export class StarField implements OnInit {
  stars: Star[] = [];

  ngOnInit(): void {
    this.stars = Array.from({ length: 120 }, (_, i) => {
      const delay = Math.random() * 4;
      return {
        id: i,
        x: Math.random() * 100,
        y: Math.random() * 100,
        size: Math.random() * 2 + 0.5,
        opacity: Math.random() * 0.6 + 0.1,
        delay,
        duration: 3 + delay,
      };
    });
  }
}
