/* ФинТабло — shared interactions */

async function loadNews() {
  const list = document.querySelector('.news-list');
  if (!list) return;

  try {
    const res = await fetch('/api/news');
    const data = await res.json();

    // Собираем уникальные страны для фильтра
    const countries = [...new Set(data.map(n => n.countryCode).filter(Boolean))];
    renderCountryFilter(countries, data);

    renderNews(data, 'all', 'all');

  } catch (e) {
    console.error('Ошибка загрузки новостей:', e);
  }
}

function renderCountryFilter(countries, data) {
  // Ищем или создаём блок фильтра стран
  let countryBar = document.getElementById('country-filter');
  if (!countryBar) {
    countryBar = document.createElement('div');
    countryBar.id = 'country-filter';
    countryBar.className = 'toolbar';
    countryBar.style.marginBottom = '12px';
    const toolbar = document.querySelector('.toolbar');
    toolbar.parentNode.insertBefore(countryBar, toolbar.nextSibling);
  }

  const flagMap = { by: '🇧🇾', ru: '🇷🇺' };

  countryBar.innerHTML = `
    <button class="chip is-active" data-country-chip="all">Все страны</button>
    ${countries.map(code => `
      <button class="chip" data-country-chip="${code}">
        ${flagMap[code] || ''} ${code.toUpperCase()}
      </button>`).join('')}`;

  countryBar.querySelectorAll('[data-country-chip]').forEach(chip => {
    chip.addEventListener('click', () => {
      countryBar.querySelectorAll('[data-country-chip]')
        .forEach(c => c.classList.remove('is-active'));
      chip.classList.add('is-active');

      const activeCategory = document.querySelector('[data-news-chip].is-active')
        ?.getAttribute('data-news-chip') || 'all';
      renderNews(data, chip.getAttribute('data-country-chip'), activeCategory);
    });
  });
}

function renderNews(data, countryFilter, categoryFilter) {
  const list = document.querySelector('.news-list');
  list.innerHTML = '';

  const filtered = data.filter(news => {
    const matchCountry = countryFilter === 'all' || news.countryCode === countryFilter;
    const matchCategory = categoryFilter === 'all' || news.category === categoryFilter;
    return matchCountry && matchCategory;
  });

  if (filtered.length === 0) {
    list.innerHTML = `<div class="empty-state">Новостей не найдено</div>`;
    return;
  }

  filtered.forEach(news => {
    const date = news.pubT
      ? new Date(news.pubT).toLocaleDateString('ru-RU',
          { day: 'numeric', month: 'long' })
      : '—';

    const flagMap = { by: '🇧🇾', ru: '🇷🇺' };
    const flag = flagMap[news.countryCode] || '';

    const item = document.createElement('a');
    item.href = news.articleUrl || '#';
    item.target = '_blank';
    item.className = 'news-item';
    item.setAttribute('data-news-item', '');
    item.setAttribute('data-category', news.category || 'other');

    item.innerHTML = `
      <div class="news-date">${date}</div>
      <div>
        ${news.imageUrl
        ? `<img src="${news.imageUrl}"
          style="width:100%;height:120px;object-fit:cover;
                 border-radius:6px;margin-bottom:10px;display:block;"
          alt=""
          onerror="this.remove()">`
        : ''}
        <div style="display:flex;align-items:center;gap:8px;margin-bottom:8px;">
          <span class="news-tag">${news.sourceName || 'Новость'}</span>
          <span style="font-family:var(--f-mono);font-size:11px;
                       color:var(--text-faint);letter-spacing:0.05em;">
            ${flag} ${(news.countryCode || '').toUpperCase()}
          </span>
        </div>
        <h3 class="news-title">${news.title || '—'}</h3>
        <p class="news-snippet" style="overflow:hidden;display:-webkit-box;
           -webkit-line-clamp:2;-webkit-box-orient:vertical;">
          ${news.snippet || ''}
        </p>
      </div>`;

    list.appendChild(item);
  });

  // Переинициализируем фильтр категорий
  const newsChips = document.querySelectorAll('[data-news-chip]');
  newsChips.forEach(chip => {
    chip.addEventListener('click', () => {
      newsChips.forEach(c => c.classList.remove('is-active'));
      chip.classList.add('is-active');
      const activeCountry = document.querySelector('[data-country-chip].is-active')
        ?.getAttribute('data-country-chip') || 'all';
      renderNews(data, activeCountry, chip.getAttribute('data-news-chip'));
    });
  });
}