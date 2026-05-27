// Auth Context
import { useAuth } from "../../context/AuthContext";

// Styles
import "./style.css";

const Home = () => {
    const { user, logout } = useAuth();

    return (
        <div className="dash-wrapper">
            <aside className="dash-sidebar">
                <div className="dash-brand">
                    <div className="dash-logo-sq"> S </div>
                    <span> App Manager </span>
                </div>

                <nav className="dash-nav">
                    <a href="#" className="dash-nav-link">
                        <span> 🔑 </span> Credenciais
                    </a>
                </nav>

                <div className="dash-user-badge">
                    <div className="dash-user-info">
                        <span className="dash-username"> { user?.name || "Erro ao carregar nome" } </span>
                        <span className="dash-status"> Online </span>
                    </div>
                </div>
            </aside>

            <main className="dash-main">
                <header className="dash-header">
                    <div className="dash-header-left">
                        <h1> Visão Geral do Perfil </h1>
                        <p> Dados recuperados via OAuth 2.0 </p>
                    </div>

                    <button onClick={logout} className="dash-btn-logout">
                        Sair
                    </button>
                </header>

                <div className="dash-content-grid">
                    <section className="dash-card dash-profile-hero">
                        <div className="dash-hero-content">
                            <div className="dash-avatar-container">
                                <img src={user?.urlPicture} alt="User" />
                            </div>

                            <div className="dash-hero-text">
                                <h2> { user?.name || "Usuário" } </h2>

                                <p> { user?.email || "email@exemplo.com" } </p>
                            </div>
                        </div>
                    </section>

                    <div className="dash-info-row">
                        <article className="dash-card dash-info-box">
                            <label> Último Login </label>
                            <strong> { user?.lastDateLogin } </strong>
                        </article>

                        <article className="dash-card dash-info-box">
                            <label> Provedor de Login </label>

                            <strong className="provider-google"> 
                                { user?.providerName?.toUpperCase() || "N/A" } 
                            </strong>
                        </article>
                    </div>

                    <section className="dash-card">
                        <h3 className="dash-card-title"> Escopos de Acesso (Scopes) </h3>

                        <div className="dash-scopes">
                            {user?.scopes.map((scope, index) => (
                                <span key={index} className="dash-scope-pill">
                                    { scope }
                                </span>
                            ))}
                        </div>
                    </section>
                </div>
            </main>
        </div>
    )
}

export default Home;