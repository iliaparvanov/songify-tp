FactoryBot.define do
    factory :artist do
        name { Faker::Music::RockBand.name }
    end
end