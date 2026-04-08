import { Component } from '@angular/core';
import { Footer } from '../../components/layouts/footer/footer';
import { HeroSection } from "../../components/sections/hero-section/hero-section";
import { FeaturesSection } from "../../components/sections/features-section/features-section";

@Component({
  selector: 'app-landing-page',
  imports: [Footer, HeroSection, FeaturesSection],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.scss',
})
export class LandingPage {}
