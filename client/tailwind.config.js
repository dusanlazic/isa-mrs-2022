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
        '120': '30rem'
      }
    },
  },
  plugins: [],
}