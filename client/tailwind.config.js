const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  mode: 'jit',
  purge: [
    './public/**/*.html',
    './src/**/*.{js,jsx,ts,tsx,vue}',
  ],
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  plugins: [require("daisyui")],
  daisyui: {
    styled: false,
    themes: false,
  },
  theme: {
    extend: {
      fontFamily: {
        'display': ['Montserrat', ...defaultTheme.fontFamily.serif], 
        'opensans': ['Open Sans', ...defaultTheme.fontFamily.serif],   
      },
      spacing: {
        '84': '21rem',
        '108': '27rem',
        '120': '30rem',
        '140': '35rem',
        '160': '40rem'
      },
      colors: {
        'pine': '#127475',
        'light-pine': '#0E9594',
        'raisin-black': '#272635',
        'whitish-gray': '#DBDBDB',
        'silver-accent': '#e2e6df',
        'baby-green-darker': '#cbeda4',
        'gray-green': '#e9f2ef'
      }
    },
  }

}