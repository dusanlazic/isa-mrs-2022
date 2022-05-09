import './App.css';
import HomePage from './pages/HomePage'
import ProfilePage from './pages/ProfilePage';
import ProfileEditorPage from './pages/ProfileEditorPage';
import RegistrationPage from './pages/RegistrationPage';
import CreateAdventurePage from './pages/CreateAdventurePage';
import LoginPage from './pages/LoginPage';

import NotFound from './pages/NotFound'

import { BrowserRouter as Router, Route, Routes, Head } from 'react-router-dom'
import RegistrationConfirmation from './pages/RegistrationConfirmationPage';

function App() {
  return (
    <Router>
      <div className="App min-h-screen">
        {/* Navbar Component */}
        <div className="h-full">
            <Routes>
              <Route path="/" exact element={<HomePage/>}/>
              <Route path="/new/adventure" exact element={<CreateAdventurePage/>}/>
              <Route path="/resort/:id" exact element={<ProfilePage/>}/>
              <Route path="/client/:id" exact element={<ProfilePage/>}/>
              <Route path="/boat/:id" exact element={<ProfilePage/>}/>
              <Route path="/account" exact element={<ProfileEditorPage/>}/>
              <Route path="/register" exact element={<RegistrationPage/>}/>
              <Route path="/login" exact element={<LoginPage/>}/>
              <Route path="/confirm-registration/:token" exact element={<RegistrationConfirmation/>}/>
              <Route path="*" element={<NotFound/>}/>
            </Routes>
          </div>
      </div>
    </Router>
  );
}

export default App;
