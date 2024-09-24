describe('Create and delete vehicle', () => {
  it('Clicks the Sign in button', () => {
    // Открываем страницу
    cy.visit('http://localhost:8080');

    // Переходим к окну логина
    cy.contains('Sign in').click();

    // Вводим имя пользователя и пароль
    cy.get('#username').type('Sublimee');
    cy.get('#password').type('123456');

    // Выполняем логин
    cy.get('button[type="submit"]').click();

    // Переходим к списку транспортных средств первого предприятия
    cy.contains('Show vehicles').click();

    // Переходим на последнюю страницу
    cy.contains(/Page \d+ of \d+/).then(($el) => {
      const totalPages = $el.text().match(/Page \d+ of (\d+)/)[1];
      cy.get('input[placeholder="choose page to go"]').type(totalPages);
      cy.contains('button', 'Go').click();
    });

    // Запоминаем идентификатор последнего транспортного средства
    cy.get('#vehicles tbody tr').then((rows) => {
      cy.wrap(rows).eq(rows.length - 3).find('td').first().invoke('text').then((lastVehicleId) => {
        // Выводим идентификатор в лог
        cy.wrap(lastVehicleId).as('lastVehicleId');
        cy.log('Last vehicle ID is: ' + lastVehicleId);
      });
    });

    // Выводим идентификатор последнего в списке транспортного средства
    cy.get('@lastVehicleId').then((lastVehicleId) => {
      cy.log('Last vehicle ID is:  ' + lastVehicleId);
    });

    // Переходим к созданию нового транспортного средства
    cy.contains('a.btn.btn-success', 'Create vehicle').click();

    // Создаем новое транспортное средство
    cy.get('#price').type('1000');
    cy.get('#drivers').select('1');
    cy.get('input[type="submit"][value="Submit"]').click();

    // Переходим на последнюю страницу
    cy.contains(/Page \d+ of \d+/).then(($el) => {
      const totalPages = $el.text().match(/Page \d+ of (\d+)/)[1];
      cy.get('input[placeholder="choose page to go"]').type(totalPages);
      cy.contains('button', 'Go').click();
    });

    // Запоминаем идентификатор последнего на данный момент транспортного средства
    cy.get('#vehicles tbody tr').then((rows) => {
      cy.wrap(rows).eq(rows.length - 3).find('td').first().invoke('text').then((newVehicleId) => {
        // Выводим идентификатор в лог
        cy.wrap(newVehicleId).as('newVehicleId');
        cy.log('New last vehicle ID is: ' + newVehicleId);
      });
    });

    // Проверяем, что идентификаторы различаются
    cy.get('@newVehicleId').then((newVehicleId) => {
      cy.get('@lastVehicleId').then((lastVehicleId) => {
        expect(newVehicleId).not.to.equal(lastVehicleId);
      });
    });

    // Удаляем последнее транспортное средство
    cy.get('a.btn.btn-danger').last().click();

    // Переходим на последнюю страницу
    cy.contains(/Page \d+ of \d+/).then(($el) => {
      const totalPages = $el.text().match(/Page \d+ of (\d+)/)[1];
      cy.get('input[placeholder="choose page to go"]').type(totalPages);
      cy.contains('button', 'Go').click();
    });

    // Проверяем, что текущий последний идентификатор транспортного средства совпадает с исходным до теста
    cy.get('#vehicles tbody tr').then((rows) => {
      cy.wrap(rows).eq(rows.length - 3).find('td').first().invoke('text').then((currentLastVehicleId) => {
        cy.get('@lastVehicleId').then((lastVehicleId) => {
          // Проверяем, что идентификаторы совпадают
          expect(currentLastVehicleId).equal(lastVehicleId);
        });
      });
    });
  });
});