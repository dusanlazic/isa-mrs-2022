import './App.css';
import HomePage from './pages/HomePage';
import SearchPage from './pages/SearchPage'
import ProfilePage from './pages/ProfilePage';
import ProfileEditorPage from './pages/ProfileEditorPage';
import RegistrationPage from './pages/RegistrationPage';
import LoginPage from './pages/LoginPage';
import RegistrationConfirmation from './pages/RegistrationConfirmationPage';
import CreateAdventurePage from './pages/CreateAdventurePage';
import UpdateAdventurePage from './pages/UpdateAdventurePage';
import CreateBoatPage from './pages/CreateBoatPage';
import UpdateBoatPage from './pages/UpdateBoatPage';
import CreateResortPage from './pages/CreateResortPage';
import UpdateResortPage from './pages/UpdateResortPage';
import RegistrationRequestsPage from './pages/RegistrationRequestsPage';
import Navbar from './components/util/Navbar';

import NotFound from './pages/NotFound';

import { BrowserRouter as Router, Route, Routes, Head } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="App min-h-screen">
        {/* Navbar Component */}
        <Navbar />
        <div className="h-full">
            <Routes>
              <Route path="/" exact element={<HomePage/>}/>
              <Route path="/ads/resorts" exact element={<SearchPage/>}/>
              <Route path="/ads/boats" exact element={<SearchPage/>}/>
              <Route path="/ads/adventures" exact element={<SearchPage/>}/>
              <Route path="/adventure/new" exact element={<CreateAdventurePage/>}/>
              <Route path="/adventure/:id/edit" exact element={<UpdateAdventurePage/>}/>
              <Route path="/adventure/:id" exact element={<ProfilePage/>}/>
              <Route path="/resort/new" exact element={<CreateResortPage/>}/>
              <Route path="/resort/:id" exact element={<ProfilePage/>}/>
              <Route path="/resort/:id/edit" exact element={<UpdateResortPage/>}/>
              <Route path="/client/:id" exact element={<ProfilePage/>}/>
              <Route path="/boat/new" exact element={<CreateBoatPage/>}/>
              <Route path="/boat/:id" exact element={<ProfilePage/>}/>
              <Route path="/boat/:id/edit" exact element={<UpdateBoatPage/>}/>
              <Route path="/account" exact element={<ProfileEditorPage/>}/>
              <Route path="/register" exact element={<RegistrationPage/>}/>
              <Route path="/login" exact element={<LoginPage/>}/>
              <Route path="/confirm-registration/:token" exact element={<RegistrationConfirmation/>}/>
              <Route path="/admin/registration-requests" exact element={<RegistrationRequestsPage/>}/>
              <Route path="*" element={<NotFound/>}/>
            </Routes>
          </div>
      </div>
    </Router>
  );
}

export default App;
