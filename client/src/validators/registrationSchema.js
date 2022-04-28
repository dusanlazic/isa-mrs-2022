import * as yup from 'yup';

const pageTwoSchema = yup.object().shape({
  email: yup.string().email("Please enter a valid email.").required("Email is required."),
  password: yup.string().required("Password is required.").min(8, "Password must be 8 characters or longer."),
  confirmPassword: yup.string()
  .oneOf([yup.ref('password'), null], 'Passwords must match.')
  .required('Confirm Password is required.'),
});

const phoneRegExp = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/

const pageThreeSchema = yup.object().shape({
  firstName: yup.string().required("First name is required.")
  .min(2, "First name too short.").max(20, "First name too long."),
  lastName: yup.string().required("Last name is required.")
  .min(2, "Last name too short.").max(32, "Last name too long."),
  phoneNumber: yup.string().matches(phoneRegExp, 'Phone number is not valid.')
});

const pageFourSchema = yup.object().shape({
  address: yup.string().required("Address is required."),
  city: yup.string().required("City is required.")
});

export { pageTwoSchema, pageThreeSchema, pageFourSchema }