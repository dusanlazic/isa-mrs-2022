const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        'display': ['Montserrat', ...defaultTheme.fontFamily.serif], 
        'opensans': ['Open Sans', ...defaultTheme.fontFamily.serif],   
      },
      spacing: {
        '84': '21rem',
        '120': '30rem',
        '140': '35rem',
        '160': '40rem'
      },
      colors: {
        'skobeloff': '#127475',
        'viridian-green': '#0E9594',
        'raisin-black': '#272635',
        'gainsboro': '#DBDBDB',
        'baby-green': '#e9f5db'
      }
    },
  },
  plugins: [],
}